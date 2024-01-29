package com.example.toylanguage_intellij.Model.Statements;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.toylanguage_intellij.Controller.MyException;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.IDictionary;
import com.example.toylanguage_intellij.Model.ProgramStateComponents.ProgramState;
import com.example.toylanguage_intellij.Model.Types.Type;

public interface IStatement {
    ProgramState execute(ProgramState programState) throws MyException, FileNotFoundException, IOException;
    IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException;
}