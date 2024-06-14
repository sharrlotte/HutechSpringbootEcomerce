package vn.edu.hutech.bai2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.hutech.bai2.model.Slide;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {
}
