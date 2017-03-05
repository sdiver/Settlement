package mapper;

import model.caseInfo;
import model.forensicsInfo;
import model.settleinfo;
import model.settlementStatistics;

import java.util.List;
import java.util.Map;

public interface settlementSearchMapper {

    settlementStatistics settleStatistics(Map<Object, Object> map);

    List<settleinfo> settleSearch(Map<Object, Object> map);

    caseInfo settleinfo(int caseCode);

    List<forensicsInfo> forensicsInfoList(String case_forensics);
}
