package web.controlevacinacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.controlevacinacao.model.Status;
import web.controlevacinacao.model.Vacina;
import web.controlevacinacao.repository.queries.vacina.VacinaQueries;

import java.util.List;

public interface VacinaRepository extends JpaRepository<Vacina, Long>, VacinaQueries {
    List<Vacina> findByStatus(Status status);

    Vacina findByCodigoAndStatus(Long codigo, Status status);
}
