//package pl.specialist.searchexpert.repositories;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import pl.specialist.searchexpert.domains.specialist.Province;
//import pl.specialist.searchexpert.domains.specialist.Specialist;
//import pl.specialist.searchexpert.repositories.specialist.SpecialistRepo;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@RunWith(SpringRunner.class)
//@Transactional
//@DataJpaTest
//public class SpecialistRepoUnitTest {
//
//    /*Dependency Injection*/
//    @Autowired
//    private SpecialistRepo specialistRepo;
//
//    /*Five various specialists helpful to implement tests*/
//
//    private static final Specialist first_specialist = new Specialist( "Tony", "Hawk", Province.MAZOWIECKIE, "Warszawa", "plumber", "795034234","danke1@op.pl",3.4,3);
//    private static final Specialist second_specialist = new Specialist( "Anthony", "Joshua", Province.DOLNOSLASKIE, "Katowice", "mechanic", "503942034","enigma532@op.pl",2.1,6);
//    private static final Specialist third_specialist = new Specialist( "Mike", "Tyson", Province.WIELKOPOLSKIE, "Poznań", "painter", "593032123","enigma32@op.pl",4.8,12);
//    private static final Specialist fourth_specialist = new Specialist( "Martin", "Dudziuk", Province.MAZOWIECKIE, "Warszawa", "tutor", "739403593","danke123@op.pl",1.8,31);
//    private static final Specialist fifth_specialist = new Specialist( "Charles", "Montana", Province.LUBUSKIE, "Opole", "automatic", "512493053","danke12@op.pl",3.3,9);
//
//    /*Searching a group of specialists at once*/
//    private static final List<Specialist> groupOfSpecialists = new ArrayList<>();
//
//    /*Initialize Specialists professions and add to group for searching a group of specialists*/
//    @BeforeClass
//    public static void initializeSpecialistsIntoRepository() {
//        // given
//        groupOfSpecialists.add(first_specialist);
//        groupOfSpecialists.add(second_specialist);
//        groupOfSpecialists.add(third_specialist);
//        groupOfSpecialists.add(fourth_specialist);
//        groupOfSpecialists.add(fifth_specialist);
//    }
//    @BeforeEach
//    public void setUp(){
//        specialistRepo.saveAll(groupOfSpecialists);
//    }
//
//    @Test
//    public void whenSaveSpecialist_thenIsNotNull() {
//        specialistRepo.save(first_specialist);
//        assertThat(specialistRepo.findAll()).isNotNull();
//    }
//
//    @Test
//    public void whenDeleteAllSpecialists_thenReturnEmptyRepo() {
//        specialistRepo.saveAll(groupOfSpecialists);
//        assertThat(specialistRepo.findAll()).isNotEmpty();
//        specialistRepo.deleteAll();
//        assertThat(specialistRepo.findAll()).isEmpty();
//    }
//
//    @Test
//    public void whenDeleteSpecialistBySpecialistId_thenCannotFindSpecialistBySpecialistId(){
//        specialistRepo.saveAll(groupOfSpecialists);
//        String specialistIdOfSpecialistWhichWillDelete = first_specialist.getSpecialistId();
//        assertThat(specialistRepo.count()).isEqualTo(5);
//        specialistRepo.delete(first_specialist);
//        assertThat(specialistRepo.findBySpecialistId(specialistIdOfSpecialistWhichWillDelete)).isNotEqualTo(first_specialist);
//    }
//
//    @Test
//    public void whenFindAllSpecialists_thenReturnRepositorySize() {
//        specialistRepo.saveAll(groupOfSpecialists);
//        assertThat(specialistRepo.count()).isEqualTo(5);
//    }
//
//    @Test
//    public void whenFindGroupOfSpecialistsByNameAndSurname_thenReturnSpecialist() {
//        specialistRepo.saveAll(groupOfSpecialists);
//        Iterable<Specialist> listOfSearchedSpecialists = specialistRepo.findSpecialistsByNameAndSurname("Marta","Dudziuk");
//
//        ArrayList<Specialist> testList = new ArrayList<>();
//        for(Specialist specialist : listOfSearchedSpecialists){
//            if(specialist.getName().equals("Marta")
//                    && specialist.getSurname().equals("Dudziuk")) testList.add(specialist);
//        }
//        ArrayList<Specialist> actualList = new ArrayList<>();
//        for(Specialist specialist : groupOfSpecialists){
//            if(specialist.getName().equals("Marta")
//                    && specialist.getSurname().equals("Dudziuk"))  actualList.add(specialist);
//        }
//
//        // then
//        /*Name*/
//        assertThat(testList
//                .stream()
//                .map(Specialist::getName).collect(Collectors.toList()))
//                .isEqualTo(actualList
//                        .stream()
//                        .map(Specialist::getName).collect(Collectors.toList()));
//        /*Surname*/
//        assertThat(testList
//                .stream()
//                .map(Specialist::getSurname).collect(Collectors.toList()))
//                .isEqualTo(actualList
//                        .stream()
//                        .map(Specialist::getSurname).collect(Collectors.toList()));
//    }
//
//    @Test
//    public void whenFindSpecialistByMail_thenReturnSpecialistWithThisMail(){
//        specialistRepo.saveAll(groupOfSpecialists);
//        String mailOfFirstSpecialist = groupOfSpecialists.get(0).getMail();
//
//        assertThat(specialistRepo.findByMail(mailOfFirstSpecialist).getMail()).isEqualTo(first_specialist.getMail());
//    }
//
//
//
//    @Test
//    public void whenFindGroupOfSpecialistsByProvinceAndCityAndProfession_thenReturnSpecialist() {
//// when
//        specialistRepo.saveAll(groupOfSpecialists);
//        Iterable<Specialist> listOfSearchedSpecialists = specialistRepo.findSpecialistsByProvinceAndCityAndProfession(Province.MAZOWIECKIE, "Warszawa","plumber");
//
//        ArrayList<Specialist> testList = new ArrayList<>();
//        for(Specialist specialist : listOfSearchedSpecialists){
//            if(specialist.getProvince().equals(Province.MAZOWIECKIE)
//                    && specialist.getCity().equals("Warszawa")
//                    && specialist.getProfession().equals("plumber")) testList.add(specialist);
//        }
//        ArrayList<Specialist> actualList = new ArrayList<>();
//        for(Specialist specialist : groupOfSpecialists){
//            if(specialist.getProvince().equals(Province.MAZOWIECKIE)
//                    && specialist.getCity().equals("Warszawa")
//                    && specialist.getProfession().equals("plumber")) actualList.add(specialist);
//        }
//
//        // then
//        /*Province*/
//        assertThat(testList
//                .stream()
//                .map(Specialist::getProvince).collect(Collectors.toList()))
//                .isEqualTo(actualList
//                        .stream()
//                        .map(Specialist::getProvince).collect(Collectors.toList()));
//        /*City*/
//        assertThat(testList
//                .stream()
//                .map(Specialist::getCity).collect(Collectors.toList()))
//                .isEqualTo(actualList
//                        .stream()
//                        .map(Specialist::getCity).collect(Collectors.toList()));
//        /*Profession*/
//        assertThat(testList
//                .stream()
//                .map(Specialist::getProfession).collect(Collectors.toList()))
//                .isEqualTo(actualList
//                        .stream()
//                        .map(Specialist::getProfession).collect(Collectors.toList()));
//    }
//}