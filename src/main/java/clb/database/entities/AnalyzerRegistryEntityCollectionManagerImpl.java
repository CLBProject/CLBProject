package clb.database.entities;

import java.io.Serializable;

import org.springframework.stereotype.Service;

@Service
public class AnalyzerRegistryEntityCollectionManagerImpl implements AnalyzerRegistryEntityCollectionManager, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String collectionName;
    
    
    @Override
    public String getCollectionName() {
        return collectionName;
    }

    @Override
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}
