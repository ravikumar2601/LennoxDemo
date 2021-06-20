package utilsBrowser.browserSetting;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.texen.util.FileUtil;

import java.io.File;
import java.io.IOException;

public class DirectoryUtil {
    private String dirName;
    private String fullPath;
    private String userHomePath = System.getProperty("user.home");
    private static final Log logger = LogFactory.getLog(DirectoryUtil.class);


    private String getDirName() {
        return dirName;
    }

    public String getFullPath() {
        return fullPath;
    }

    private void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    private void createDirectoryInUserHome() {
        if (isDirExist()) {
            removeDirectoryInUserHome();
        }
        FileUtil.mkdir(getFullPath());
    }

    public void removeDirectoryInUserHome() {
        try {
            FileUtils.forceDelete(new File(getFullPath()));
        } catch (IOException e) {
            logger.error("Can not remove directory: " + getDirName());
        }
    }


    public DirectoryUtil(String dirName) {
        this.dirName = dirName;
        setFullPath(userHomePath + "\\" + dirName);
    }

    public String produceDirAndPath() {
        createDirectoryInUserHome();
        return getFullPath();
    }

    private boolean isDirExist() {
        return new File(getFullPath()).exists();
    }
}
