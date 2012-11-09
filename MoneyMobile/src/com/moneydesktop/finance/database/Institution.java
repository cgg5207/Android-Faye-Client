package com.moneydesktop.finance.database;

import java.util.List;
import com.moneydesktop.finance.database.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table INSTITUTION.
 */
public class Institution extends BusinessObject  {

    private Long id;
    private java.util.Date createdOn;
    private String institutionId;
    private String instructions;
    private String name;
    private String phone;
    private Integer popularity;
    private Integer status;
    private java.util.Date updatedOn;
    private String url;
    private long businessObjectId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient InstitutionDao myDao;

    private BusinessObjectBase businessObjectBase;
    private Long businessObjectBase__resolvedKey;

    private List<Bank> banks;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Institution() {
    }

    public Institution(Long id) {
        this.id = id;
    }

    public Institution(Long id, java.util.Date createdOn, String institutionId, String instructions, String name, String phone, Integer popularity, Integer status, java.util.Date updatedOn, String url, long businessObjectId) {
        this.id = id;
        this.createdOn = createdOn;
        this.institutionId = institutionId;
        this.instructions = instructions;
        this.name = name;
        this.phone = phone;
        this.popularity = popularity;
        this.status = status;
        this.updatedOn = updatedOn;
        this.url = url;
        this.businessObjectId = businessObjectId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInstitutionDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.util.Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(java.util.Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public java.util.Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(java.util.Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getBusinessObjectId() {
        return businessObjectId;
    }

    public void setBusinessObjectId(long businessObjectId) {
        this.businessObjectId = businessObjectId;
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
    public synchronized List<Bank> getBanks() {
        if (banks == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BankDao targetDao = daoSession.getBankDao();
            banks = targetDao._queryInstitution_Banks(id);
        }
        return banks;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetBanks() {
        banks = null;
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
    	setInstitutionId(id);
    }
    
    public String getExternalId() {
    	return getInstitutionId();
    }
    // KEEP METHODS END

}
