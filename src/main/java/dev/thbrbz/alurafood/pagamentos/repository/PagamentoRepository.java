package dev.thbrbz.alurafood.pagamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.thbrbz.alurafood.pagamentos.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
