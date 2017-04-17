package mapper;

import model.newsInfo;

import java.util.List;
import java.util.Map;

public interface newsMapper {
    void addPhoto(Map<Object, Object> info);

    List<newsInfo> listPhoto();

    void deletePhoto(int id);

    int getOrder(int upId);

    void updateOrder(Map<Object, Object> infotwo);
}
