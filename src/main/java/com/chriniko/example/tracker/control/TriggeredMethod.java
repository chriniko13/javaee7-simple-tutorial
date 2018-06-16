package com.chriniko.example.tracker.control;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TriggeredMethod {

    private String className;
    private String methodName;
    private List<String> methodArgNames;
}
