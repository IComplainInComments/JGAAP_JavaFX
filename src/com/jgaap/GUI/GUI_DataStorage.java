package com.jgaap.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jgaap.backend.Languages;
import com.jgaap.generics.Language;
import com.jgaap.util.Document;

import javafx.scene.control.TreeItem;

public class GUI_DataStorage {

    private static ArrayList<Object> docTypesList;
    private static ArrayList<Document> UnknownDocumentList;
    private static ArrayList<Document> KnownDocumentList;
    private static ArrayList<Document> DocumentList;
    private static ArrayList<String> AuthorList;
    private static ArrayList<Language> LanguagesMasterList;

    public GUI_DataStorage() {
        docTypesList = new ArrayList<Object>();
        UnknownDocumentList = new ArrayList<Document>();
        KnownDocumentList = new ArrayList<Document>();
        DocumentList = new ArrayList<Document>();
        AuthorList = new ArrayList<String>();
        LanguagesMasterList = new ArrayList<Language>();

        populateLanguageMasterList();
    }

    public boolean addData(String which, Object data) {
        boolean status = false;
        switch (which) {
            case "docTypes":
                docTypesList.add(data);
                status = true;
                break;
            case "unknownDocuments":
                UnknownDocumentList.add((Document) data);
                status = true;
                break;
            case "knownDocuments":
                KnownDocumentList.add((Document) data);
                status = true;
                break;
            case "documents":
                DocumentList.add((Document) data);
                status = true;
                break;
            case "authors":
                AuthorList.add((String) data);
                status = true;
                break;
            case "languages":
                LanguagesMasterList.add((Language) data);
                status = true;
                break;
            default:
                status = false;
                break;
        }
        return status;
    }
    public boolean addData(String which, List<Object> data) {
        boolean status = false;
        switch (which) {
            case "docTypes":
                docTypesList.addAll(data);
                status = true;
                break;
            case "unknownDocuments":
                UnknownDocumentList.addAll(objToDoc(data));
                status = true;
                break;
            case "knownDocuments":
                KnownDocumentList.addAll(objToDoc(data));
                status = true;
                break;
            case "documents":
                DocumentList.addAll(objToDoc(data));
                status = true;
                break;
            case "authors":
                AuthorList.addAll(objToString(data));
                status = true;
                break;
            case "languages":
                LanguagesMasterList.addAll(objToLanguage(data));
                status = true;
                break;
            default:
                status = false;
                break;
        }
        return status;
    }

    public boolean removeData(String which, Object data) {
        boolean status = false;
        switch (which) {
            case "docTypes":
                docTypesList.remove(data);
                status = true;
                break;
            case "unknownDocuments":
                UnknownDocumentList.remove((Document) data);
                status = true;
                break;
            case "knownDocuments":
                KnownDocumentList.remove((Document) data);
                status = true;
                break;
            case "documents":
                DocumentList.remove((Document) data);
                status = true;
                break;
            case "authors":
                AuthorList.remove((String) data);
                status = true;
                break;
            case "languages":
                LanguagesMasterList.remove((Language) data);
                status = true;
                break;
            default:
                status = false;
                break;
        }
        return status;
    }

    public boolean removeData(String which, int num) {
        boolean status = false;
        switch (which) {
            case "docTypes":
                docTypesList.remove(num);
                status = true;
                break;
            case "unknownDocuments":
                UnknownDocumentList.remove(num);
                status = true;
                break;
            case "knownDocuments":
                KnownDocumentList.remove(num);
                status = true;
                break;
            case "documents":
                DocumentList.remove(num);
                status = true;
                break;
            case "authors":
                AuthorList.remove(num);
                status = true;
                break;
            case "languages":
                LanguagesMasterList.remove(num);
                status = true;
                break;
            default:
                status = false;
                break;
        }
        return status;
    }
    private void populateLanguageMasterList(){
        for (Language language : Languages.getLanguages()) {
         if (language.showInGUI())
            LanguagesMasterList.add(language);
      }
    }
    private List<Document> objToDoc(List<Object> data){
        List<Document> temp = data.stream()
        .map(element -> (Document) element)
        .collect(Collectors.toList());
        return temp;
    }
    private List<String> objToString(List<Object> data){
        List<String> temp = data.stream()
        .map(element -> (String) element)
        .collect(Collectors.toList());
        return temp;
    }
    private List<Language> objToLanguage(List<Object> data){
        List<Language> temp = data.stream()
        .map(element -> (Language) element)
        .collect(Collectors.toList());
        return temp;
    }

    public List<Object> getDocTypesList() {
        return docTypesList;
    }

    public List<Document> getUnknownDocumentList() {
        return UnknownDocumentList;
    }

    public List<Document> getKnownDocumentList() {
        return KnownDocumentList;
    }

    public List<Document> getDocumentList() {
        return DocumentList;
    }

    public List<String> getAuthorList() {
        return AuthorList;
    }

    public List<Language> getLanguagesMasterList() {
        return LanguagesMasterList;
    }
    public List<String> getLanguagesMasterListAsString() {
        List<String> temp = new ArrayList<String>();
        for(int i = 0; i < LanguagesMasterList.size(); i++){
            temp.add(LanguagesMasterList.get(i).displayName());
        }
        return temp;
    }
    public List<TreeItem<String>> getAuthorListAsTreeViews() {
        List<TreeItem<String>> temp = new ArrayList<TreeItem<String>>();
        for(int i = 0; i < AuthorList.size(); i++){
            TreeItem<String> item = new TreeItem<String>(AuthorList.get(i));
            temp.add(item);
        }
        return temp;
    }

}
