# Prompts — how this repo was built, verbatim

1.
- vreau 1 proj nou numit lesson-oauth in workspace
- 1 Keycloak ca-n proj spring lansat pe local, nu-n docker
- 2 applicatii web cu cate 1 endpoint accesibil din browser, fara fe, doar un GET
- 1 microserviciu chemat de una din app cu acess tokenul userului
- Dacă userul vrea să acceseze a doua aplicație după ce s-a logat în prima, să intre fără parolă, cu single sign-on. Foarte important să ții tot codul cât de minim poate fi conceput și, când crezi că e minim, îți ridici un agent să te critice, să încerce să mai elimine din text, ca să-mi fie cât mai ușor să înțeleg mișcările din acel dans.
- Pentru debugging, aș vrea ca atât aplicațiile web, cât și microserviciile să logheze tokenul sau tokenii primiți și trimiși.
- Paralizează cât de mult poți cu subagenți, pentru a termina repede.
- Cand vezi ca merge, zi-mi si dupa aia scrie testele.

2. Îmi place mai mult ideea asta [`@AuthenticationPrincipal OidcUser user, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client`] Să lucrez cu Security Context Holder. Get Principal. Oricum se rezolvă și cu adapterul ăsta special de... Era mai simplu

3. schimba

4. Vreau ca aplicația unu... Nu, aplicația doi să fie înlocuită în loc de aplicația unu

5. redenumeste proj principal in spring-lesson-oauth

6. appi publica pe github ca public

7. Fă și mie o poză, un deployment diagram, un C4, ceva, să văd și eu care e povestea, unde e browserul, unde e aplicație, cum se leagă între ele. Un plan TML pentru proiectul ăsta

8. N-ai nevoie deloc de register de OAuth Authorization Client. [`@RegisteredOAuth2AuthorizedClient`] E, tu doar complici lucrurile. Atât cum poți să scoți totul din Security Context Holder

9. Aici la fel [app1: `@AuthenticationPrincipal OidcUser user, @RegisteredOAuth2AuthorizedClient`], simplifică cât de mult poți, fără să adaugi concepte în plus. Ce-s astea adăugările astea? Complică puțin lucrurile.

10. O să meargă aplicația asta și pe Windows. Am impresia că sunt niște shell script-uri pe aici.

11. Tot ce îți voi spune să faci de acum încolo mi-l pui și [în] repo, da?
