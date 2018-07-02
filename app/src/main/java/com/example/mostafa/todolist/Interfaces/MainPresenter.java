package com.example.mostafa.todolist.Interfaces;

import com.example.mostafa.todolist.Models.TODOitem;

public interface MainPresenter {
    public void onAddBtnClicked();
    public void onItemClicked(int position);
    public void onLogoutBtnClicked();
}
