package clb.ui.enums;

public enum AnalysisTypes
{

    QUICK_ANALYSIS("Quick Analysis"),
    ADVANCED_ANALYSIS( "Advanced Analysis"),
    BENCHMARKETING("Benchmarketing");
    
    private String label;
    
    AnalysisTypes(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
    
}
