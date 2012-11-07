package com.moneydesktop.finance.database;

import java.util.List;
import com.moneydesktop.finance.database.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
import org.json.JSONObject;
// KEEP INCLUDES END
/**
 * Entity mapped to table BANK.
 */
public class Bank extends ObjectBase  {

    private Long id;
    private String bankId;
    private String bankName;
    private java.util.Date dateCreated;
    private String defaultClassId;
    private String deleteAction;
    private Boolean isLinked;
    private java.util.Date lastRefreshDate;
    private String logoId;
    private Integer processStatus;
    private Integer status;
    private String statusDescription;
    private Integer statusFlags;
    private String statusInstructions;
    private Long institutionId;
    private long businessObjectId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient BankDao myDao;

    private Institution institution;
    private Long institution__resolvedKey;

    private BusinessObjectBase businessObjectBase;
    private Long businessObjectBase__resolvedKey;

    private List<BankAccount> bankAccounts;
    private List<Location> locations;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Bank() {
    }

    public Bank(Long id) {
        this.id = id;
    }

    public Bank(Long id, String bankId, String bankName, java.util.Date dateCreated, String defaultClassId, String deleteAction, Boolean isLinked, java.util.Date lastRefreshDate, String logoId, Integer processStatus, Integer status, String statusDescription, Integer statusFlags, String statusInstructions, Long institutionId, long businessObjectId) {
        this.id = id;
        this.bankId = bankId;
        this.bankName = bankName;
        this.dateCreated = dateCreated;
        this.defaultClassId = defaultClassId;
        this.deleteAction = deleteAction;
        this.isLinked = isLinked;
        this.lastRefreshDate = lastRefreshDate;
        this.logoId = logoId;
        this.processStatus = processStatus;
        this.status = status;
        this.statusDescription = statusDescription;
        this.statusFlags = statusFlags;
        this.statusInstructions = statusInstructions;
        this.institutionId = institutionId;
        this.businessObjectId = businessObjectId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBankDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public java.util.Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(java.util.Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDefaultClassId() {
        return defaultClassId;
    }

    public void setDefaultClassId(String defaultClassId) {
        this.defaultClassId = defaultClassId;
    }

    public String getDeleteAction() {
        return deleteAction;
    }

    public void setDeleteAction(String deleteAction) {
        this.deleteAction = deleteAction;
    }

    public Boolean getIsLinked() {
        return isLinked;
    }

    public void setIsLinked(Boolean isLinked) {
        this.isLinked = isLinked;
    }

    public java.util.Date getLastRefreshDate() {
        return lastRefreshDate;
    }

    public void setLastRefreshDate(java.util.Date lastRefreshDate) {
        this.lastRefreshDate = lastRefreshDate;
    }

    public String getLogoId() {
        return logoId;
    }

    public void setLogoId(String logoId) {
        this.logoId = logoId;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Integer getStatusFlags() {
        return statusFlags;
    }

    public void setStatusFlags(Integer statusFlags) {
        this.statusFlags = statusFlags;
    }

    public String getStatusInstructions() {
        return statusInstructions;
    }

    public void setStatusInstructions(String statusInstructions) {
        this.statusInstructions = statusInstructions;
    }

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }

    public long getBusinessObjectId() {
        return businessObjectId;
    }

    public void setBusinessObjectId(long businessObjectId) {
        this.businessObjectId = businessObjectId;
    }

    /** To-one relationship, resolved on first access. */
    public Institution getInstitution() {
        if (institution__resolvedKey == null || !institution__resolvedKey.equals(institutionId)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            InstitutionDao targetDao = daoSession.getInstitutionDao();
            institution = targetDao.load(institutionId);
            institution__resolvedKey = institutionId;
        }
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
        institutionId = institution == null ? null : institution.getId();
        institution__resolvedKey = institutionId;
    }

    /** To-one relationship, resolved on first access. */
    public BusinessObjectBase getBusinessObjectBase() {
        if (businessObjectBase__resolvedKey == null || !businessObjectBase__resolvedKey.equals(businessObjectId)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BusinessObjectBaseDao targetDao = daoSession.getBusinessObjectBaseDao();
            businessObjectBase = targetDao.load(businessObjectId);
            businessObjectBase__resolvedKey = businessObjectId;
        }
        return businessObjectBase;
    }

    public void setBusinessObjectBase(BusinessObjectBase businessObjectBase) {
        if (businessObjectBase == null) {
            throw new DaoException("To-one property 'businessObjectId' has not-null constraint; cannot set to-one to null");
        }
        this.businessObjectBase = businessObjectBase;
        businessObjectId = businessObjectBase.getId();
        businessObjectBase__resolvedKey = businessObjectId;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public synchronized List<BankAccount> getBankAccounts() {
        if (bankAccounts == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BankAccountDao targetDao = daoSession.getBankAccountDao();
            bankAccounts = targetDao._queryBank_BankAccounts(id);
        }
        return bankAccounts;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetBankAccounts() {
        bankAccounts = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public synchronized List<Location> getLocations() {
        if (locations == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LocationDao targetDao = daoSession.getLocationDao();
            locations = targetDao._queryBank_Locations(id);
        }
        return locations;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetLocations() {
        locations = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    
    public void setExternalId(String id) {
    	setBankId(id);
    }
    
    public String getExternalId() {
    	return getBankId();
    }
    
    public static Bank saveBank(JSONObject json, boolean delete) {
    	
    	Bank bank = (Bank) saveObject(json, Bank.class, delete);
    	
    	return bank;
    }
    
    // KEEP METHODS END

}
