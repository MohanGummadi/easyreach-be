/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html","./src/**/*.{ts,tsx,js,jsx}"],
  theme: {
    extend: {
      colors: {
        brand: {
          ink: {
            DEFAULT: "#0B3A4A",
            700: "#114C5F",
            600: "#1F6B80"
          },
          accent: {
            DEFAULT: "#E4891A",
            700: "#C26B00",
            300: "#F5B753"
          },
          surface: "#FFFFFF",
          surfaceAlt: "#FFF6EA"
        }
      },
      boxShadow: {
        subtle: "0 1px 2px rgba(0,0,0,.04), 0 1px 6px rgba(0,0,0,.04)"
      },
      borderRadius: {
        md: "8px",
        xl: "12px"
      }
    }
  },
  plugins: []
}
