package com.mesago.mscatalogomenu.repository;

import com.mesago.mscatalogomenu.entity.CategoriaMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaMenuRepository extends JpaRepository<CategoriaMenu, Long> {
}
