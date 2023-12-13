/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.ImportDetails;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface ImportDetailsFacadeLocal {

    void create(ImportDetails importDetails);

    void edit(ImportDetails importDetails);

    void remove(ImportDetails importDetails);

    ImportDetails find(Object id);

    List<ImportDetails> findAll();

    List<ImportDetails> findRange(int[] range);

    int count();

    public List<ImportDetails> findByImportID(int importID);
}
