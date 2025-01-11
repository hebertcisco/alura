package br.com.car.adapters.bd

import br.com.car.domain.model.CarBrand
import br.com.car.domain.ports.CarBrandRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CarBrandRepository(
    private val jdbcTemplate: JdbcTemplate
) : CarBrandRepository {

    override fun listAll(): List<CarBrand> = jdbcTemplate.query(
        "SELECT * FROM car_brand;",
        CarBrandMapper()
    )

    override fun listByName(name: String): List<CarBrand> =
        jdbcTemplate.query("SELECT * FROM car_brand WHERE name LIKE ?", CarBrandMapper(), "%$name%")

    override fun updateOrSave(carBrand: CarBrand) {
        val exists = listByName(carBrand.name).firstOrNull()
        if (exists != null) {
            update(carBrand, carBrand.id)
        } else {
            save(carBrand)
        }
    }

    override fun save(carBrand: CarBrand) = jdbcTemplate.update(
        "INSERT INTO car_brand(name, code) VALUES(?,?)",
        carBrand.name,
        carBrand.code,
    )

    override fun update(carBrand: CarBrand, id: Long) = jdbcTemplate.update(
        "UPDATE car_brand SET name = ?, code = ? WHERE id = ?",
        carBrand.name,
        carBrand.code,
        id
    )

    override fun findById(id: Long): CarBrand? {
        val results = jdbcTemplate.query("SELECT * FROM car_brand WHERE id = ?", CarBrandMapper(), id)
        return if (results.isEmpty()) null else results[0]
    }

    override fun findByCode(code: Long): CarBrand? {
        val results = jdbcTemplate.query("SELECT * FROM car_brand WHERE code = ?", CarBrandMapper(), code)
        return if (results.isEmpty()) null else results[0]
    }

    override fun findByName(name: String): CarBrand? {
        val results = jdbcTemplate.query("SELECT * FROM car_brand WHERE name = ?", CarBrandMapper(), name)
        return if (results.isEmpty()) null else results[0]
    }
}
