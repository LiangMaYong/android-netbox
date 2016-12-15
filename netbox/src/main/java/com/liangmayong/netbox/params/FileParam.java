package com.liangmayong.netbox.params;

import android.os.Bundle;

import java.io.File;

/**
 * Created by LiangMaYong on 2016/9/14.
 */
public class FileParam {
    // extras
    private Bundle extras = null;
    // local path
    private String localPath = "";
    // name
    private String name;


    /**
     * FileParam
     *
     * @param localPath localPath
     */
    public FileParam(String localPath) {
        this.localPath = localPath;
        this.name = localPath;
    }

    /**
     * FileParam
     *
     * @param file file
     */
    public FileParam(File file) {
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
     * setName
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getName
     *
     * @return name
     */
    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return localPath;
    }
}
