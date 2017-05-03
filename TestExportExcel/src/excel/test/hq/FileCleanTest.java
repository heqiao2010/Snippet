package excel.test.hq;

import java.io.File;

public class FileCleanTest {
	
	private String TEMP_FILE_DIR_PATH = "E:\\FeigeDownload";
	
	private Long fileExpiredTimeInHour = 1L;
	
	public Long getFileExpiredTimeInHour() {
		return fileExpiredTimeInHour;
	}

	public void setFileExpiredTimeInHour(Long fileExpiredTimeInHour) {
		this.fileExpiredTimeInHour = fileExpiredTimeInHour;
	}

	public void updateCache() throws Exception {
		File tempDir = new File(TEMP_FILE_DIR_PATH);
		if(null != tempDir && tempDir.isDirectory() ){
			// 遍历目录
			File[] temFiles = tempDir.listFiles();
			for(int i=0; null!=temFiles && i<temFiles.length; i++){
				if(null != temFiles[i] && temFiles[i].isFile() && isFileExpired(temFiles[i])){
					System.out.println("File [" + temFiles[i].getName() + "] is expired.");
					// 删除文件
					if(temFiles[i].delete()){
						System.out.println("File [" + temFiles[i].getName() + "] delete failed!");
					} else {
						System.out.println("File [" + temFiles[i].getName() + "] was deleted!");
					}
				}
			}
		} else {
			System.out.println("Wrong temp file directory path: " + TEMP_FILE_DIR_PATH);
		}
	}

	public Boolean isFileExpired(File file) {
		if(null != file && file.exists() && file.isFile() ){
			long lastModifiedTime = file.lastModified();
			long now = System.currentTimeMillis();
			return now - lastModifiedTime > Math.abs(getFileExpiredTimeInHour() * 60 * 60 * 1000);
		} else {
			return false;
		}
	}
	
	public static void main(String args[]){
		FileCleanTest ftest = new FileCleanTest();
		try {
			ftest.updateCache();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
