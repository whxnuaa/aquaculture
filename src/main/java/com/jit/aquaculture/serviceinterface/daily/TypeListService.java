package com.jit.aquaculture.serviceinterface.daily;

import java.util.List;

public interface TypeListService {
    List<String> getFixedThrowName();
    List<String> getSeedName();
    List<String> getSeedBrand();
    List<String> getFeedName();
    List<String> getFeedContent();
    List<String> getMedicineName();
    List<String> getObserveName();
    List<String> getObserveContent();
    List<String> getBuyName();
    List<String> getSaleName();
}
