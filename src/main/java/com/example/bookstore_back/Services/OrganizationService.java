package com.example.bookstore_back.Services;

import com.example.bookstore_back.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    private OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService (
            OrganizationRepository organizationRepository
    ) {
        this.organizationRepository = organizationRepository;
    }
}
