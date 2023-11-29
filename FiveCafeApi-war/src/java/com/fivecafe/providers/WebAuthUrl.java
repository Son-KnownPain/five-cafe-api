package com.fivecafe.providers;

import java.util.HashMap;
import java.util.List;

public interface WebAuthUrl {
    public List<String> signInUrls();
    public HashMap<String, List<String>> roleUrls();
}
