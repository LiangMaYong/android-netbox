package com.liangmayong.netbox.params;

/**
 * Created by LiangMaYong on 2016/9/14.
 */
public class ParamFile {
    // name
    private String name = "";
    // path
    private String path = "";

    public ParamFile(String name, String path) {
        this.name = name;
        this.path = path;
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