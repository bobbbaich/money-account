package com.bobbbaich.moneyaccount.service;

import com.bobbbaich.moneyaccount.model.Organization;

import java.util.Optional;

public interface OrganizationService {
    Optional<Organization> getCurrentOrganization();
}