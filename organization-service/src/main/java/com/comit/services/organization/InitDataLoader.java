package com.comit.services.organization;

import com.comit.services.organization.loging.model.CommonLogger;
import com.comit.services.organization.model.entity.Organization;
import com.comit.services.organization.service.OrganizationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDataLoader implements CommandLineRunner {

    @Value("${system.supperAdmin.organization}")
    private String superAdminOrganization;

    @Autowired
    OrganizationServices organizationServices;

    @Override
    public void run(String... args) {
        // Create organization
        Organization organization = organizationServices.getOrganization(superAdminOrganization);
        if (organization == null) {
            organization = new Organization();
            organization.setName(superAdminOrganization);
            organization = organizationServices.addOrganization(organization);
            if (organization == null) {
                CommonLogger.error("Error when init organization");
            }
        }
    }
}
