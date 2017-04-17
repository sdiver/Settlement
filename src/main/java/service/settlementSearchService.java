package service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

public interface settlementSearchService {
    Map<Object,Object> settleStatistics(int userId, Date caseStartTime, Date caseEndTime,
                                        int regionId, int forensicsType, int status, int Page, int pagePerNum);

    Map<Object,Object> settleinfo(String caseCode, int userId);

    Map<Object,Object> mySettles(int userId, int status);

    Map<Object,Object> downloadFile(String caseCode, HttpServletResponse response);
}
