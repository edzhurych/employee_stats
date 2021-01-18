package com.example.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
internal interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Employee)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Specialty)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: EmployeeToSpecialty)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: Employee)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: Specialty)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: EmployeeToSpecialty)

    @Query("SELECT employeeId FROM relations WHERE employeeId=:employeeId AND specialtyId=:specialtyId LIMIT 1")
    fun checkRelations(employeeId: Int, specialtyId: Int): Int?

    @Query("SELECT employeeId FROM employee WHERE employeeId=:id LIMIT 1")
    fun checkEmployee(id: Int): Int?

    @Query("SELECT specialtyId FROM specialty WHERE specialtyId=:id LIMIT 1")
    fun checkSpecialty(id: Int): Int?

    @Query("SELECT * FROM specialty ORDER BY name")
    fun getSpecialties(): List<Specialty>

    @Query("SELECT * FROM employee INNER JOIN relations ON employee.employeeId = relations.employeeId WHERE specialtyId=:specialtyId")
    fun getEmployeesBy(specialtyId: Int): Flow<List<Employee>>

    @Query("SELECT * FROM employee WHERE employee.employeeId=:employeeId")
    fun getEmployeeBy(employeeId: Int): Flow<Employee>

    @Query("SELECT * FROM specialty INNER JOIN relations ON specialty.specialtyId = relations.specialtyId WHERE employeeId=:employeeId")
    fun getSpecialtiesFor(employeeId: Int): List<Specialty>

    @Transaction
    fun insertData2(listEmployee: List<Employee>) {

        listEmployee.forEachIndexed { index, employee ->
            employee.employeeId = index
        }
        val listSpec = listEmployee.flatMap {
            it.specialty ?: listOf()
        }.distinctBy { it.specialtyId }
        val relations = listEmployee.relations()

        listEmployee.forEach {
            it.insertOrUpdate(
                checkItemById = { checkEmployee(it.employeeId) },
                insert = { insert(it) },
                update = { update(it) }
            )
        }

        listSpec.forEach {
            it.insertOrUpdate(
                checkItemById = { checkSpecialty(it.specialtyId) },
                insert = { insert(it) },
                update = { update(it) }
            )
        }

        relations.forEach {
            if (checkEmployee(it.employeeId) != null && checkSpecialty(it.specialtyId) != null) {
                it.insertIfNotExist(
                    checkItemById = { checkRelations(it.employeeId, it.specialtyId) },
                    insert = { insert(it) },
                )
            }
        }
    }

    @Transaction
    fun insertData(
        listEmployee: List<Employee>,
        listSpec: List<Specialty>,
        relations: List<EmployeeToSpecialty>
    ) {
        listEmployee.forEach {
            it.insertOrUpdate(
                checkItemById = { checkEmployee(it.employeeId) },
                insert = { insert(it) },
                update = { update(it) }
            )
        }

        listSpec.forEach {
            it.insertOrUpdate(
                checkItemById = { checkSpecialty(it.specialtyId) },
                insert = { insert(it) },
                update = { update(it) }
            )
        }

        relations.forEach {
            if (checkEmployee(it.employeeId) != null && checkSpecialty(it.specialtyId) != null) {
                it.insertIfNotExist(
                    checkItemById = { checkRelations(it.employeeId, it.specialtyId) },
                    insert = { insert(it) },
                )
            }
        }
    }
}