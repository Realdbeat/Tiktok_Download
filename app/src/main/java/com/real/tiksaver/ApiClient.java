package com.real.tiksaver;
import android.os.AsyncTask;
import okhttp3.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ApiClient {

    private OkHttpClient client;

    public ApiClient() {
        client = new OkHttpClient();
    }

    public void makePostRequestAndDownloadResponse(String url, String requestBody, String destinationPath, String size) {
        new DownloadTask().execute(url, requestBody, destinationPath,size);
    }

    private class DownloadTask extends AsyncTask<String, Integer, Void> {
       String errors = "";
        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            String requestBody = params[1];
            String destinationPath = params[2];
            Long size =  Long.parseLong(params[3]);
            
      
      
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected response code: " + response);
                }

                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    saveFile(responseBody.byteStream(), destinationPath, size);
                }
            } catch (IOException e) {
                e.printStackTrace();
                errors = e.toString();
            }

            return null;
        }

        private void saveFile(InputStream inputStream, String destinationPath, long contentLength) throws IOException {
            try (OutputStream outputStream = new FileOutputStream(destinationPath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                long totalBytesRead = 0;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                    publishProgress((int) ((totalBytesRead * 100) / contentLength));
                }
                outputStream.flush();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Update UI with progress percentage
            int progress = values[0];
            MainActivity.getmInstanceActivity()._UpdateDownload("Download progress: " + progress + "%",progress);
            
        }

        @Override
        protected void onPostExecute(Void result) {
            // Execution completed, perform any UI updates or callback if needed
            if(!errors.isEmpty()){
 MainActivity.getmInstanceActivity()._Download_Error(errors,"IOException Error");
            }else{
            	MainActivity.getmInstanceActivity()._UpdateDownload("Download completed!",100);
            }
            
        }
    }
}
