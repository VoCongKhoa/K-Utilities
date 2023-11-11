package kutilities.repository.excel;

import kutilities.domain.entity.excel.SaleRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRecordRepository extends JpaRepository<SaleRecord, String> {
    String TABLE = "sale_record";
}
