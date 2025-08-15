// Initialize theme management
function initTheme() {
  console.log('[Theme] Initializing theme management...');
  
  const themeToggle = document.getElementById('theme-toggle');
  const themeIcon = document.getElementById('theme-icon');

  console.log(`[Theme] Elements found - Toggle: ${themeToggle ? 'Yes' : 'No'}, Icon: ${themeIcon ? 'Yes' : 'No'}`);

  // Get current theme from localStorage or system preference
  function getCurrentTheme() {
    const savedTheme = localStorage.getItem('theme');
    const systemPrefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
    
    console.log(`[Theme] Saved theme from localStorage: ${savedTheme || 'None'}`);
    console.log(`[Theme] System prefers dark: ${systemPrefersDark}`);
    
    const theme = savedTheme || (systemPrefersDark ? 'dark' : 'light');
    console.log(`[Theme] Determined current theme: ${theme}`);
    
    return theme;
  }

  // Apply theme to the document
  function applyTheme(theme) {
    console.log(`[Theme] Applying theme: ${theme}`);
    
    document.documentElement.setAttribute('data-theme', theme);
    console.log(`[Theme] data-theme attribute set to: ${theme}`);
    
    if (themeIcon) {
      themeIcon.textContent = theme === 'dark' ? '☀️' : '🌙';
      console.log(`[Theme] Theme icon updated to: ${themeIcon.textContent}`);
    }
    
    // Set cookie for server-side persistence (optional)
    document.cookie = `theme=${theme}; path=/; max-age=${60 * 60 * 24 * 365}`;
    console.log(`[Theme] Cookie set for theme: ${theme}`);
  }

  // Toggle between themes
  function toggleTheme() {
    console.log('[Theme] Theme toggle clicked');
    
    const currentTheme = document.documentElement.getAttribute('data-theme');
    console.log(`[Theme] Current theme before toggle: ${currentTheme}`);
    
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    console.log(`[Theme] New theme after toggle: ${newTheme}`);
    
    localStorage.setItem('theme', newTheme);
    console.log(`[Theme] Saved new theme to localStorage: ${newTheme}`);
    
    applyTheme(newTheme);
  }

  // Initialize
  console.log('[Theme] Initializing theme...');
  const currentTheme = getCurrentTheme();
  applyTheme(currentTheme);

  // Set up event listener
  if (themeToggle) {
    themeToggle.addEventListener('click', toggleTheme);
    console.log('[Theme] Click event listener added to theme toggle');
  } else {
    console.error('[Theme] ERROR: Could not find theme toggle element!');
  }

  // Enable transitions after page load
  document.body.classList.add('loaded');
  console.log('[Theme] Added "loaded" class to body');
}

console.log('[Theme] Script loaded, waiting for DOM...');

// Wait for DOM to load
document.addEventListener('DOMContentLoaded', function() {
  console.log('[Theme] DOM fully loaded, initializing theme...');
  try {
    initTheme();
    console.log('[Theme] Initialization complete');
  } catch (error) {
    console.error('[Theme] ERROR during initialization:', error);
  }
});