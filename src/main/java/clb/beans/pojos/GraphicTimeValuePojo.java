package clb.beans.pojos;

import java.util.Date;
import java.util.Map;

import clb.global.QuickAnalysisVariables;

public class GraphicTimeValuePojo
{
    public Date graphicTiem;
    public Map<QuickAnalysisVariables,Object> graphicVariableValues;

    public GraphicTimeValuePojo( Date graphicTiem, Map<QuickAnalysisVariables, Object> graphicVariableValues ) {
        super();
        this.graphicTiem = graphicTiem;
        this.graphicVariableValues = graphicVariableValues;
    }
    
    public Date getGraphicTiem() {
        return graphicTiem;
    }
    public Map<QuickAnalysisVariables, Object> getGraphicVariableValues() {
        return graphicVariableValues;
    }
    
}
