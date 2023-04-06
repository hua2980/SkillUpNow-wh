package com.skillupnow.demo.service;

import com.skillupnow.demo.model.dto.TeachPlanTreeDto;
import com.skillupnow.demo.service.Iml.TeachPlanServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeachPlanServiceImplTest {

  @Autowired
  TeachPlanServiceImpl teachPlanServiceImpl;

  @Test
  void testFindTeachPlanByCourseId() {
    List<TeachPlanTreeDto> tree = teachPlanServiceImpl.getTeachPlanTreeByCourseId(4L);
    Assertions.assertNotNull(tree);
    // there are 3 root nodes
    Assertions.assertEquals(3, tree.size());
    // there are 4 nodes in the third root node
    Assertions.assertEquals(4, tree.get(2).getTeachPlanTreeNodes().size());
    // the children nodes are sorted in order
    Assertions.assertEquals(1, tree.get(2).getTeachPlanTreeNodes().get(0).getOrderBy());
    Assertions.assertEquals(2, tree.get(2).getTeachPlanTreeNodes().get(1).getOrderBy());
    Assertions.assertEquals(3, tree.get(2).getTeachPlanTreeNodes().get(2).getOrderBy());
    Assertions.assertEquals(4, tree.get(2).getTeachPlanTreeNodes().get(3).getOrderBy());
  }
}
