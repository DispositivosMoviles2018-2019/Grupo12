package ec.edu.uce.applauching;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.Menu;

import java.io.File;

public class SystemLauncherActivity extends Activity {

    private Intent browserIntent, phoneIntent, mapIntent, mailIntent, contactIntent, marketIntent, smsIntent;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        browserIntent = new Intent();

        browserIntent.setAction(Intent.ACTION_WEB_SEARCH);
        browserIntent.putExtra(SearchManager.QUERY, "puppies");
        
        phoneIntent = new Intent();
        phoneIntent.setAction(Intent.ACTION_DIAL);
        phoneIntent.setData(Uri.parse("tel:8885551234"));
        
        mapIntent = new Intent();
        mapIntent.setAction(Intent.ACTION_VIEW);
        mapIntent.setData(Uri.parse("geo:37.422,-122.084"));

        mailIntent = new Intent();
        mailIntent.setAction(Intent.ACTION_SEND);
        mailIntent.setType("message/rfc822");
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"recipient@gmail.com"});
        mailIntent.putExtra(Intent.EXTRA_CC, new String[] {"carbon@gmail.com"});
        mailIntent.putExtra(Intent.EXTRA_BCC, new String[] {"blind@gmail.com"});
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email Subject");
        mailIntent.putExtra(Intent.EXTRA_TEXT, "Body Text");
        mailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"fileOne.txt")));

        
        smsIntent = new Intent();
        smsIntent.setAction(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", "8885551234");
        smsIntent.putExtra("sms_body", "SMS Body");
        
        contactIntent = new Intent();
        contactIntent.setAction(Intent.ACTION_PICK);
        contactIntent.setData(ContactsContract.Contacts.CONTENT_URI);
        
        marketIntent = new Intent();
        marketIntent.setAction(Intent.ACTION_VIEW);
        marketIntent.setData(Uri.parse("market://search?q=basketball"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Browser").setIntent(browserIntent);
        menu.add("Phone").setIntent(phoneIntent);
        menu.add("Map").setIntent(mapIntent);
        menu.add("Mail").setIntent(Intent.createChooser(mailIntent, "Mail Client"));
        menu.add("SMS").setIntent(smsIntent);
        menu.add("Contacts").setIntent(contactIntent);
        menu.add("Market").setIntent(marketIntent);
        return true;
    }

}