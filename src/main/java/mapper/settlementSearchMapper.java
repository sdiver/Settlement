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

    caseInfo settleinfo(String caseCode);

    List<forensicsInfo> forensicsInfoList(String case_forensics);

    List<caseInfo> mySettles(Map<Object, Object> map);
}
