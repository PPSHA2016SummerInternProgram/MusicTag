package com.paypal.musictag.dao.usingdb;

import java.util.UUID;
import org.apache.ibatis.annotations.Param;

public interface RecordingMapper {
    String getWorkMBID( @Param(value = "recordingGid") UUID recordingGid );
}
