package br.com.car.adapters.bd

import br.com.car.domain.model.CarBrand
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class CarBrandMapper : RowMapper<CarBrand> {

    override fun mapRow(rs: ResultSet, rowNum: Int) = CarBrand(
        id = requireNotNull(rs.getLong("id")),
        name = requireNotNull(rs.getString("name")),
        code = requireNotNull(rs.getLong("code"))
    )
}
