package com.oplayer.orunningplus.utils.javautils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ProjectName: ORunningPlus
 * @Package: com.oplayer.common.javautils
 * @ClassName: ParsedAd
 * @Description: java类作用描述
 * @Author: Ben
 * @CreateDate: 2019/9/6 16:49
 */
public    //数据解析实体
class RecordDate {
    public   byte flags = 0;
    public  String localName = "";
    public  List<UUID> uuids = new ArrayList<>();
    public  short manufacturer = 0;

    @Override
    public String toString() {
        return "ParsedAd{" +
                " \n   flags=" + flags +
                ",\n  localName='" + localName + '\'' +
                ",\n  uuids=" + uuids +
                ",\n  manufacturer=" + manufacturer +
                '}';
    }

    public RecordDate() {
    }

    public RecordDate(byte flags, String localName, List<UUID> uuids, short manufacturer) {
        this.flags = flags;
        this.localName = localName;
        this.uuids = uuids;
        this.manufacturer = manufacturer;
    }

    public byte getFlags() {
        return flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public List<UUID> getUuids() {
        return uuids;
    }

    public void setUuids(List<UUID> uuids) {
        this.uuids = uuids;
    }

    public short getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(short manufacturer) {
        this.manufacturer = manufacturer;
    }
}
