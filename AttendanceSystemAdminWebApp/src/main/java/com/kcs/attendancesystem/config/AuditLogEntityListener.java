package com.kcs.attendancesystem.config;

import static com.kcs.attendancesystem.enums.Action.DELETED;
import static com.kcs.attendancesystem.enums.Action.INSERTED;
import static com.kcs.attendancesystem.enums.Action.UPDATED;
import static javax.transaction.Transactional.TxType.MANDATORY;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import com.kcs.attendancesystem.core.AuditLog;
import com.kcs.attendancesystem.core.BaseEntity;
import com.kcs.attendancesystem.enums.Action;

public class AuditLogEntityListener<T extends BaseEntity> {

	@PrePersist
	public void prePersist(T clazz) {
		perform(clazz, INSERTED);
	}

	@PreUpdate
	public void preUpdate(T clazz) {
		perform(clazz, UPDATED);
	}

	@PreRemove
	public void preRemove(T clazz) {
		perform(clazz, DELETED);
	}

	@Transactional(MANDATORY)
	private void perform(T clazz, Action action) {
		AuditLog auditLog = new AuditLog();
		auditLog.setEntityName(clazz.getClass().getSimpleName());
		auditLog.setRefTableId(clazz.getId());
		auditLog.setContent(clazz.toString());
		auditLog.setAction(action);
		
		EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
        entityManager.persist(auditLog);
	}
}
