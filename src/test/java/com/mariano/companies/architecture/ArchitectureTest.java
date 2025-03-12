package com.mariano.companies.architecture;

import com.mariano.companies.domain.exceptions.CompaniesException;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;
import com.tngtech.archunit.library.dependencies.SliceRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchitectureTest {

    JavaClasses importedClasses = new ClassFileImporter().withImportOption(new ImportOption.DoNotIncludeTests()).importPackages("com.mariano.companies");

    @Test
    void api_should_only_depend_on_domain() {
        ArchRule rule = noClasses().that().resideInAnyPackage("..api..")
                .should().dependOnClassesThat().resideInAnyPackage("..infrastructure..");
        rule.check(importedClasses);
    }

    @Test
    void infrastructure_should_only_depend_on_domain() {
        ArchRule rule = noClasses().that().resideInAnyPackage("..infrastructure..")
                .should().dependOnClassesThat().resideInAnyPackage("..presentation..");
        rule.check(importedClasses);
    }

    @Test
    void domain_should_not_depend_on_api_or_infrastructure() {
        ArchRule rule = noClasses().that().resideInAnyPackage("..domain..")
                .should().dependOnClassesThat().resideInAnyPackage("..api..", "..infrastructure..");
        rule.check(importedClasses);
    }

    @Test
    void infrastructure_should_not_have_circular_dependencies() {
        SliceRule rule = SlicesRuleDefinition.slices()
                .matching("..infrastructure.(*)..")
                .should().beFreeOfCycles();
        rule.check(importedClasses);
    }

    @Test
    void domain_should_not_have_circular_dependencies() {
        SliceRule rule = SlicesRuleDefinition.slices()
                .matching("..domain.(*)..")
                .should().beFreeOfCycles();
        rule.check(importedClasses);
    }

    @Test
    void api_should_not_have_circular_dependencies() {
        SliceRule rule = SlicesRuleDefinition.slices()
                .matching("..api.(*)..")
                .should().beFreeOfCycles();
        rule.check(importedClasses);
    }

    @Test
    void exceptions_should_extend_from_CompaniesException() {
        ArchRule rule = classes()
                .that().areAssignableTo(Exception.class)
                .should().beAssignableTo(CompaniesException.class);
        rule.check(importedClasses);
    }

    @Test
    void no_generic_exceptions_should_be_thrown() {
        ArchRule rule = noClasses()
                .should(GeneralCodingRules.THROW_GENERIC_EXCEPTIONS);
        rule.check(importedClasses);
    }
}