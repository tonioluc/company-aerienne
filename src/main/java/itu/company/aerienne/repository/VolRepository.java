package itu.company.aerienne.repository;

import itu.company.aerienne.model.Vol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VolRepository extends JpaRepository<Vol, Integer> {

        @Query("SELECT v FROM Vol v WHERE v.idTrajet IN " +
            "(SELECT t.idTrajet FROM Trajet t WHERE t.idAeroportDepart = :idDepart AND t.idAeroportArrive = :idArrive)")
        List<Vol> findByAeroports(@Param("idDepart") Integer idAeroportDepart, @Param("idArrive") Integer idAeroportArrive);

    @Query("SELECT v FROM Vol v WHERE CAST(v.dateHeureDepart AS LocalDate) = :date")
    List<Vol> findByDate(@Param("date") LocalDate date);

    List<Vol> findByIdAvion(Integer idAvion);
}
