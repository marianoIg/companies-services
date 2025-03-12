package com.mariano.companies.infrastructure.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COMPANIES")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
@ToString(exclude = "transfers")
public class CompanyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANIES_SEQ")
    @SequenceGenerator(name = "COMPANIES_SEQ", sequenceName = "companies_seq", allocationSize = 1)
    private Long id;

    @Column(name = "cuit", nullable = false, unique = true, length = 15)
    private String cuit;

    @Column(name = "business_name", nullable = false, unique = true, length = 50)
    private String businessName;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private List<CompanyTransferEntity> transfers = new ArrayList<>();
}