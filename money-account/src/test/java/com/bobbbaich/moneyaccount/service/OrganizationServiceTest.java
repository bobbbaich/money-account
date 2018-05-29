package com.bobbbaich.moneyaccount.service;

import com.bobbbaich.moneyaccount.model.Organization;
import com.bobbbaich.moneyaccount.repository.OrganizationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrganizationServiceTest {
    @Mock
    private OrganizationRepository organizationRepository;
    @InjectMocks
    private OrganizationServiceImpl organizationService;

    private Organization organization;

    @Before
    public void setUp() {
        organization = Organization.builder()
                .id("org_id").total(10L).transactions(new ArrayList<>())
                .build();
    }


    @Test
    public void getCurrentOrganization() {
        when(organizationRepository.findAll()).thenReturn(Collections.singletonList(organization));

        Optional<Organization> currentOrganization = organizationService.getCurrentOrganization();

        assertEquals(of(organization), currentOrganization);
    }
}