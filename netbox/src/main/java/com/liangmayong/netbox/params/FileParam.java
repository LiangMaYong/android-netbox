package com.liangmayong.netbox.params;

import java.io.File;

/**
 * Created by LiangMaYong on 2016/9/14.
 */
public class FileParam {
    // name
    private String name = "";
    // path
    private String path = "";

    public FileParam(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public FileParam(File file) {
        this(file.getName(), file.getPath());
    }

    /**
     * getPath
     *
     * @return path
     */
    public String getPath() {
        if (path == null) {
            return "";
        }
        return path;
    }

    /**
     * setPath
     *
     * @param path path
     */
    public void setPath(String path) {
        this.path = path;
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
     * setName
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "File{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
