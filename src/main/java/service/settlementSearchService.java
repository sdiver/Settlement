package service;

import java.util.Date;
import java.util.Map;

public interface settlementSearchService {
    Map<Object,Object> settleStatistics(int userId, Date caseStartTime, Date caseEndTime,
                                        int regionId, int forensicsType, int status, int Page, int pagePerNum);

    Map<Object,Object> settleinfo(int caseCode, int userId);

}
