package com.novi.gymmanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GymManagementApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymManagementApiApplication.class, args);
    }

    //todo • 1. Technisch ontwerp schrijven ============================================================================
    //Bevat een titelblad, inleiding en inhoudsopgave. In het documenten zitten geen verwijzingen naar afbeeldingen en informatie buiten het document zelf.
    //Beschrijft het probleem en de manier waarop deze applicatie dat probleem oplost.
    //Beschrijft wat de applicatie moet kunnen middels een sommering van 25 functionele en niet functionele eisen.
    //Bevat één klassendiagram van alle entiteiten. Dit klassendiagram is taal- en platformafhankelijk en hoeft geen methodes te bevatten.
    //todo • Bevat minimaal twee volledig uitgewerkte sequentiediagrammen waarin alle architecturale lagen (controller, service, repository) voorkomen. Zorg ervoor dat deze diagrammen klasse- en methodenamen bevatten.
    //==================================================================================================================

    //2. Verantwoordingsdocument in PDF ================================================================================
    //Minimaal 5 beargumenteerde technische keuzes.
    //Een beschrijving van minimaal 5 limitaties van de applicatie en beargumentatie van de mogelijke doorontwikkelingen.
    //Een link naar jouw project op Github.
    //==================================================================================================================

    //todo • 3. Broncode Spring Boot ===================================================================================
    //Het is een REST-ful webservice die data beheert via endpoints.
    //De applicatie is beveiligd en bevat minimaal 2 en maximaal 3 user-rollen met verschillende mogelijkheden.
    //De applicatie en database zijn onafhankelijk van elkaar waardoor het mogelijk is om eventueel naar een ander database systeem te wisselen (zoals MySQL, PostgreSQL, SQLite).
    //Communicatie met de database vindt plaats door middel van repositories. De database kan in de vorm van CRUD operaties of complexere, samengestelde, queries bevraagd worden.
    //De database is relationeel en bevat minimaal een 1 one-to-one relatie en 1 one-to-many relatie.
    //todo •  De applicatie maakt het mogelijk om bestanden (zoals muziek, PDF’s of afbeeldingen) te uploaden en te downloaden. (huiswerk les van 1 november)
    //todo • alle endpoints testen op url, body, headers.
    //todo • De application context van de applicatie wordt getest met Spring Boot test, WebMvc en JUnit.
    //todo • Het systeem wordt geleverd met een valide set aan data en unit-tests worden voorzien van eigen test data.
    //==================================================================================================================

    //todo • 4. Installatie handleiding ================================================================================
    //Een inhoudsopgave en inleiding, met daarin een korte beschrijving van de functionaliteit van de applicatie en de gebruikte technieken.
    //Een lijst van benodigdheden om de applicatie te kunnen runnen (zoals applicaties, runtime environments of andere benodigdheden.
    //Een stappenplan met installatie instructies.
    //Een lijst met (test)gebruikers en user-rollen.
    //todo • Een Postman collectie, die gebruikt kan worden om jouw applicatie te testen.
    //todo • Een lijst van REST-endpoints, inclusief voorbeelden van de JSON-requests. Deze voorbeelden moeten uitgeschreven zijn zoals in Postman, zodat je ze gemakkelijk kunt selecteren, kopiëren en plakken. Hierin leg je ook uit hoe de endpoints beveiligd zijn.

}
