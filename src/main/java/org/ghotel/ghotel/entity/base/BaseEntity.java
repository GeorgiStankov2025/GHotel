package org.ghotel.ghotel.entity.base;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(name = "id")
    private UUID id;

    @Version
    private Long version;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @LastModifiedDate
    @Setter
    @Column(name = "modified_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @Setter
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    protected BaseEntity(Builder<?, ?> builder) {
        this.id = builder.id;
        this.version = builder.version;
        this.deleted=builder.deleted;
    }

    protected abstract static class Builder<C extends BaseEntity, B extends Builder<C, B>> {
        private UUID id = UUID.randomUUID();
        private Long version = 0L;
        private boolean deleted = false;

        @SuppressWarnings("unchecked")
        protected B self() {
            return (B) this;
        }

        public B id(UUID id) {
            this.id = id;
            return self();
        }

        public B version(Long version) {
            this.version = version;
            return self();
        }

        public B deleted(boolean deleted) {
            this.deleted = deleted;
            return self();
        }

        protected abstract C build();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Class<?> thisEffectiveClass = (this instanceof HibernateProxy proxy)
                ? proxy.getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        Class<?> otherEffectiveClass = (o instanceof HibernateProxy proxy)
                ? proxy.getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        if (!thisEffectiveClass.equals(otherEffectiveClass)) {
            return false;
        }
        BaseEntity other = (BaseEntity) o;

        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public final int hashCode() {
        return (this instanceof HibernateProxy proxy)
                ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

}
