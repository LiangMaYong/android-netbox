package com.liangmayong.reform;

import android.os.Bundle;

import java.io.File;

/**
 * ReformFile
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ReformFile {

    // extras
    private Bundle extras = null;
    // local path
    private String localPath = "";

    /**
     * ReformFile
     *
     * @param localPath localPath
     */
    public ReformFile(String localPath) {
        this.localPath = localPath;
    }

    /**
     * ReformFile
     *
     * @param file file
     */
    public ReformFile(File file) {
        this(file.getPath());
    }

    /**
     * setLocalPath
     *
     * @param localPath localPath
     */
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    /**
     * getLocalPath
     *
     * @return localPath
     */
    public String getLocalPath() {
        if (localPath == null) {
            return "";
        }
        return localPath;
    }

    /**
     * setExtras
     *
     * @param extras extras
     */
    public void setExtras(Bundle extras) {
        this.extras = extras;
    }

    /**
     * getExtras
     *
     * @return extras
     */
    public Bundle getExtras() {
        if (extras == null) {
            return new Bundle();
        }
        return extras;
    }
}
