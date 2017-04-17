package service;

import java.util.Map;

public interface newsService {
    Map<Object,Object> addPhoto(String pic);
    Map<Object,Object> listPhoto();
    Map<Object,Object> deletePhoto(int id);
    Map<Object,Object> movePhoto(int upId, int downId);
}
