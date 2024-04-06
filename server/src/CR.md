# Binôme: Rayane HADDAD, Manyl TIDJANI

(c'est les questions/remarques que je me suis posé tout au long du tp) -> Manyl

## Question 1:
### Tester les fonctions nommées du repository CandidateRepository.

### Mes questions/remarques
- utiliser l'annotation @SpringBootTest() afin de nous permettre de faire de l'injection.
- @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
  - Dans notre cas nous ne voulons pas faire d'appel depuis l'extérieur, mais nous avons besoin de l'injection de dépendance pour le repository, donc nous allons utiliser MOCK
  - Dans notre cas, nous ne voulons pas nous embêter à avoir une base de données postgres sur un docker, surtout pour les tests. Nous allons donc utiliser les bases de données H2, qui est une base de données embarquée directement par un système d'écriture dans un fichier → Dans notre cas, nous allons changer la propriété spring.jpa.database-platform par org.hibernate.dialect.H2Dialect
- De plus, puisque nous utilisons une base données particulière, nous devons ajouter quelques configurations. SpringBoot met à notre disposition une annotation qui nous permet de configurer automatiquement la base, en utilisant l'annotation @AutoConfigureTestDatabase
- Pour nos tests nous allons avoir besoin du repository, il nous faut donc l'injecter. Les tests n'ayant pas de constructeurs, nous allons faire de l'injection par attribut via l'annotation @Autowired

#### Maintenant, les différents tests à faire:
- nous pouvons créer nos tests [insérer test] en utilisant l'annotation @Test au début de chacun
  - Nous allons appliquer le "Given - When - Then"
- Les test se feront sur:
  - Set<CandidateEntity> findAllByTestCenterEntityCode(TestCenterCode code);
  - Set<CandidateEntity> findAllByCandidateEvaluationGridEntitiesGradeLessThan(double grade);
  - Set<CandidateEntity> findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate localDate);

- IMPORTANT ! Faire attention à la nature des relations, plus précisemment si elle est unidirectionnelle ou bidirectionnelle
  - c'est dans l'élément/attribut contenant @ManytoOne que l'on stocke notre "entité" (est-ce bien formumlé ?)
    - exemple : .candidateEntity(candidateEntity) (voir 'testFindAllByCandidateEvaluationGridEntitiesGradeLessThan()' CandidateRepositoryTest.java )

## Question 2: 

### Testez les endpoints complètement (Component, Service, Controller) :

- déjà, il faut implémenter:
  - CandidateServiceTest
  - SessionServiceTest

#### Récupération de la moyenne d'un candidat.
- tester la fonction 'public Double getCandidateAverage(Long candidateId)' dans CandidateService.java

#### Création d'une session d'examen.
 - tester la fonction 'public SessionResponse createSession(SessionCreationRequest sessionCreationRequest)' dans SessionService.java