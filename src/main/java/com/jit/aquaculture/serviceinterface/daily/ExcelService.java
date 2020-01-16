package com.jit.aquaculture.serviceinterface.daily;

import javax.servlet.http.HttpServletResponse;

public interface ExcelService {
    void exportIncomeExcel(HttpServletResponse response) throws Exception;
    void exportCostExcel(HttpServletResponse response) throws Exception;
    void exportAllExcel(HttpServletResponse response) throws Exception;
    void exportAllExcelByDate(HttpServletResponse response,String startDate,String endDate) throws Exception;
    void exportDailyThrowExcel(HttpServletResponse response) throws Exception;
    void exportDailyObserveExcel(HttpServletResponse response) throws Exception;
    void exportDailyTemplateExcel(HttpServletResponse response) throws Exception;
}
