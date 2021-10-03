package io.github.jasonheo

import org.mockito.ArgumentMatchers.{any, anyInt, anyString}
import org.mockito.IdiomaticMockito.StubbingOps
import org.mockito.MockitoSugar.{mock, when, withObjectMocked}
import org.mockito.invocation.InvocationOnMock
import org.scalatest.{BeforeAndAfterAll, FlatSpec}
import org.scalatest.Matchers.{be, convertToAnyShouldWrapper}

class DbService {
  // return: id에 해당하는 user의 name을 return
  def getName(id: String): String = {
    s"name of '${id}'"
  }

  // return: BMI(체질량 지수)를 반환
  // Answer test를 위해 height String으로 하였음
  def getBMI(heightInMeter: String, weight: Int): Double = {
    weight.toDouble / (heightInMeter.toDouble * heightInMeter.toDouble)
  }
}

object DbService {
  def getVersion(): String = "v1"
}

class MockitoTest extends FlatSpec with BeforeAndAfterAll{
  val mockDbService = mock[DbService]

  // mocking
  override def beforeAll: Unit = {
    when(mockDbService.getName(any())).thenReturn("My name")

    // Answer test
    // Answer 단축 표현: https://github.com/mockito/mockito-scala#function-answers
    when(mockDbService.getBMI(anyString(), anyInt())).thenAnswer((i: InvocationOnMock) => {
      val height: String = i.getArgument[String](0)
      val weight: Int = i.getArgument[Int](1)

      height.toDouble + weight
    })
  }

  "mockito-scala" should "mock basic features" in {
    mockDbService.getName("id") should be ("My name")

    // 원래는 20.761245674740486 이어야 정상이지만, mock을 통해서 값을 변경했으므로 61.7이 반환되어야 한다
    mockDbService.getBMI("1.7",60) should be (61.7)
  }

  "mockito-scala" should "mock Object's method" in {
    // mocking Object
    // https://github.com/mockito/mockito-scala#mocking-scala-object
    withObjectMocked[DbService.type] {
      DbService.getVersion() returns "v2"

      DbService.getVersion() should be ("v2")
    }
  }
}
