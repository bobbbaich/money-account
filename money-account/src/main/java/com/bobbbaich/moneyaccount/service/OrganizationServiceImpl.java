package com.bobbbaich.moneyaccount.service;

import com.bobbbaich.moneyaccount.model.Organization;
import com.bobbbaich.moneyaccount.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Override
    public Optional<Organization> getCurrentOrganization() {
        Optional<Organization> first = organizationRepository.findAll().stream().findFirst();
        if (first.isPresent()) {
            return first;
        } else {
            Organization organization = organizationRepository.save(initialOrganization());
            log.debug("Organization: [{}] saved.", organization);
            return Optional.of(organization);
        }

    }

    private Organization initialOrganization() {
        return Organization.builder()
                .total(0L)
                .build();
    }
}